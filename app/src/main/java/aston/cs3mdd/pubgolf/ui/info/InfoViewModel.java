package aston.cs3mdd.pubgolf.ui.info;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InfoViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public InfoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis bibendum dolor sit amet turpis vestibulum, sit amet porta purus fringilla. Proin vitae justo neque. Vestibulum facilisis, arcu ac tincidunt cursus, lorem odio lobortis nunc, eu finibus lacus sapien sit amet ex. Fusce ornare leo nec sem rutrum tincidunt. Quisque finibus nibh id urna volutpat bibendum. Etiam cursus laoreet ultrices. Fusce at sollicitudin metus. Nulla tortor turpis, imperdiet vel dolor quis, auctor tristique turpis. Nunc odio quam, blandit sit amet neque sed, convallis cursus nunc. Nulla ac egestas tortor, eget hendrerit felis.\n" +
                "\n" +
                "Sed tellus ante, molestie ac rhoncus ut, dignissim eu ex. Nunc aliquet diam ac metus scelerisque vehicula. Nullam finibus consequat congue. Nunc efficitur mauris orci, vitae vehicula nulla venenatis ullamcorper. Aliquam id sem justo. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Sed rutrum, enim et faucibus facilisis, orci massa ultricies risus, feugiat egestas sem nisi vitae dolor. Nam in feugiat nisi. Proin imperdiet pretium lacus, vitae lobortis felis pellentesque in. Nunc tincidunt quam quis leo mollis semper.\n" +
                "\n" +
                "Nulla sed ultricies est. Quisque et porta arcu. Duis sollicitudin diam magna, in cursus eros volutpat at. Vivamus id posuere ante. Quisque sit amet pellentesque sem. Cras consequat ipsum sed massa maximus rutrum. Maecenas blandit eu nisi eu vulputate. Sed sed placerat neque. Nunc metus urna, ornare tincidunt dapibus at, fermentum ac augue. Phasellus sed ex lacinia, pretium neque a, commodo purus. Donec id leo sit amet ante porta pellentesque ut eu augue. Etiam blandit id leo sed consectetur. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.\n" +
                "\n" +
                "Sed vel odio rhoncus, blandit ligula at, consectetur enim. Ut faucibus, nisi sit amet faucibus scelerisque, dolor nisi efficitur ligula, id auctor libero tortor et justo. Curabitur efficitur commodo purus a blandit. Proin at orci vel urna fringilla mattis. Pellentesque turpis ipsum, suscipit eget ipsum ut, convallis accumsan lacus. Integer ac quam eu nunc suscipit cursus et id ante. Vestibulum id sapien dignissim lorem pellentesque dignissim. Duis elit sem, ultricies sit amet purus vel, suscipit laoreet ante. Nullam sit amet malesuada enim. Nullam egestas, urna tempus gravida feugiat, dui augue pulvinar ipsum, sed rutrum lectus lacus eu elit. Phasellus placerat sapien sit amet ultricies imperdiet. Cras eget hendrerit dui, luctus interdum odio. Aliquam maximus velit a nibh hendrerit, ac consectetur orci congue. Cras viverra accumsan leo vitae mattis.\n" +
                "\n" +
                "Praesent euismod molestie ex ac pretium. Etiam sit amet justo sit amet massa pellentesque eleifend. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Curabitur mi orci, efficitur vel est vel, suscipit egestas eros. Donec tempor molestie arcu, in facilisis leo bibendum eu. Nunc mattis, tortor sit amet placerat semper, odio tortor facilisis nibh, vel aliquet eros metus quis lectus. Morbi sed purus a augue bibendum malesuada sed at felis. Sed id ligula feugiat, placerat odio at, condimentum nulla. Maecenas nec posuere velit. Aliquam erat volutpat. In sed tortor tincidunt, mattis metus eu, scelerisque nisi. Integer molestie risus sapien, non euismod nisl mollis ut. Ut quis metus orci. Pellentesque pharetra pulvinar tellus, ut fringilla nisl fermentum sed.\n" +
                "\n");
    }


    public LiveData<String> getText() {
        return mText;
    }
}