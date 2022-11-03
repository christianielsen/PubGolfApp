package aston.cs3mdd.pubgolf.ui.info;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InfoViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public InfoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec massa enim, pretium a dolor vitae, ornare elementum nisl. Donec tempus consectetur nulla, nec vehicula diam tincidunt quis. Donec sed felis consectetur, porttitor magna vitae, auctor risus. Nullam auctor, nisl vulputate ullamcorper fermentum, justo erat vulputate sapien, at congue ante nibh id urna. Donec porttitor felis elementum elit tempus imperdiet. Nunc neque velit, maximus nec justo vitae, tempor ornare neque. Quisque at dui lacus. Maecenas tempus congue lorem eu semper. Maecenas tincidunt dapibus velit a lobortis. Maecenas sed sem porta, tempor orci id, vestibulum ligula. Pellentesque lacinia, metus sed tincidunt vulputate, urna orci ornare enim, id elementum orci leo sed metus. Sed commodo justo nec lectus pretium mattis. Fusce ligula odio, semper id nisi ac, malesuada tempor urna.\n" +
                "\n");
    }

    public LiveData<String> getText() {
        return mText;
    }
}