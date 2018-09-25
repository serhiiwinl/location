package testapp.sliubetskyi.location.android.components;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import testapp.sliubetskyi.location.R;
import testapp.sliubetskyi.location.android.App;
import testapp.sliubetskyi.location.core.model.StringsIds;
import testapp.sliubetskyi.location.core.model.modules.IResourcesProvider;

@SuppressWarnings("WeakerAccess")
final public class ResourcesIdHolder extends ApplicationComponent implements IResourcesProvider {

    private static Map<Class<? extends Enum>, Map<? extends Enum, Integer>> clazzToStringValueMap = new HashMap<>();

    static {
        {
            Map<StringsIds, Integer> localization = new EnumMap<>(StringsIds.class);
            localization.put(StringsIds.LOCATION_UNKNOWN, R.string.location_unknown);
            localization.put(StringsIds.CURRENT_LOCATION, R.string.current_unknown);
            clazzToStringValueMap.put(StringsIds.class, localization);
        }
    }

    public ResourcesIdHolder(App app) {
        super(app);
    }

    @Override
    public String stringFromEnum(Enum obj) {
        return stringFromEnum(obj, app);
    }


    @NonNull
    public static String stringFromEnum(@Nullable final Enum obj, final Context context) {
        Integer resId = resFromEnum(obj);
        if (resId == null)
            return "";
        return context.getResources().getString(resId);
    }

    @Nullable
    public static Integer resFromEnum(@Nullable final Enum obj) {
        if (obj == null)
            return null;
        Map<? extends Enum, Integer> map = clazzToStringValueMap.get(obj.getDeclaringClass());
        if (map == null)
            return null;

        return map.get(obj);
    }
}