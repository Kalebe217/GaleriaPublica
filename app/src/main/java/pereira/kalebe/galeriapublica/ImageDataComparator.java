package pereira.kalebe.galeriapublica;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class ImageDataComparator extends DiffUtil.ItemCallback<ImageData> {

    //verifica se dois arquivos de foto são iguais
    @Override
    public boolean areItemsTheSame (@NonNull ImageData oldItem, @NonNull ImageData newItem) {
        return oldItem.uri.equals(newItem.uri);
    }

    //verifica se duas imagens são iguais
    @Override
    public boolean areContentsTheSame(@NonNull ImageData oldItem, ImageData newItem ) {
        return oldItem.uri.equals(newItem.uri);
    }
}