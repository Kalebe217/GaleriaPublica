package pereira.kalebe.galeriapublica;

import androidx.paging.ListenableFuturePagingSource;
import androidx.paging.PagingState;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class GalleryPagingSource extends ListenableFuturePagingSource<Integer, ImageData> {
    GalleryRepository galleryRepository;
    Integer initialLoadSize = 0;



    public GalleryPagingSource(GalleryRepository galleryRepository) {
        this.galleryRepository = galleryRepository;
    }

    @Nullable
    @Override

    public Integer getRefreshKey(@Nonnull PagingState<Integer, ImageData> pagingState) {
        return null;
    }

    //carrega pagina do GalleryRepository e a retorna encapsulada em um objeto ListenableFuture
    @Nonnull
    @Override
    public ListenableFuture<LoadResult<Integer, ImageData>> loadFuture(@Nonnull LoadParams<Integer> loadParams) {


        //verifica qual pagina foi solicitada
        Integer nextPageNumber = loadParams.getKey();
        if (nextPageNumber == null) {
            nextPageNumber = 1;
            initialLoadSize = loadParams.getLoadSize();
        }
        Integer offSet = 0;
        if (nextPageNumber == 2) {
            offSet = initialLoadSize;

        } else {
            offSet = ((nextPageNumber - 1) * loadParams.getLoadSize()) + (initialLoadSize - loadParams.getLoadSize());
        }

        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
        Integer finalOffset = offSet;
        Integer finalNextPageNumber = nextPageNumber;
        ListenableFuture<LoadResult<Integer, ImageData>> lf = service.submit(new Callable<LoadResult<Integer, ImageData>>() {
            @Override
            public LoadResult<Integer, ImageData> call() throws Exception {
                List<ImageData> imageDataList = null;
                try {
                    imageDataList = galleryRepository.loadImageData(loadParams.getLoadSize(), finalOffset);
                    Integer nextKey = null;
                    if (imageDataList.size() >= loadParams.getLoadSize()) {
                        nextKey = finalNextPageNumber + 1;

                    }
                    return new LoadResult.Page<Integer, ImageData>(imageDataList, null, nextKey);

                } catch (FileNotFoundException e) {
                    return new LoadResult.Error<>(e);
                }
            }
        });
        return lf;
    }
}