package cs48.ucsb.edu.clubhop.MarkerOptions;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import cs48.ucsb.edu.clubhop.Facebook.FacebookEvent;

/**
 * Created by Joel on 2/17/2017.
 */

public class GroupMarkerOptions extends AbstractMarkerOptions {

    @Override
    MarkerOptions generate(FacebookEvent event) {
        return new MarkerOptions()
                .position(new LatLng(event.getLocation().getLatitude(), event.getLocation().getLongitude()))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
    }
}
