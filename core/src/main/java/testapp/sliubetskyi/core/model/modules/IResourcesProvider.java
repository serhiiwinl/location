package testapp.sliubetskyi.core.model.modules;


/**
 * Helps to separate java core module and android platform dependency.
 */

public interface IResourcesProvider {
    String stringFromEnum(final Enum obj);
}
