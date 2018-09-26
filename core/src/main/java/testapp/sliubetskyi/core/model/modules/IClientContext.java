package testapp.sliubetskyi.core.model.modules;

/**
 * Impl it if you wish to provide access to all app components.
 */
public interface IClientContext {
    IPersistentData getPersistentStorage();
    ILocationManager getLocationManager();
    IAppState getAppState();
    IPermissionsManager getPermissionsManager();
    IResourcesProvider getResProvider();
}
