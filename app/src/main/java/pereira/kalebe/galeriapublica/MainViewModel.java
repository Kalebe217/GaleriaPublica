package pereira.kalebe.galeriapublica;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import java.util.List;

import kotlinx.coroutines.CoroutineScope;

public class MainViewModel extends AndroidViewModel {
    LiveData<PagingData<ImageData>> pageLv;
    int navigationOpSelected = R.id.gridViewOp;


    public MainViewModel(@NonNull Application application) {
        super(application);

        GalleryRepository galleryRepository = new GalleryRepository(application);
        GalleryPagingSource galleryPagingSource = new GalleryPagingSource(galleryRepository);
        Pager<Integer, ImageData> pager = new Pager(new PagingConfig(10), () -> galleryPagingSource);
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
        pageLv = PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager), viewModelScope);

    }

    //metodos
    public LiveData<PagingData<ImageData>> getPageLv() {
        return pageLv;
    }
    public int getNavigationOpSelected() {
        return navigationOpSelected;
    }
    public void setNavigationOpSelected(int navigationOpSelected) {
        this.navigationOpSelected = navigationOpSelected;

    }
}