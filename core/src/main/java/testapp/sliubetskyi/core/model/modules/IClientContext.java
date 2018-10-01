package testapp.sliubetskyi.core.model.modules;

import testapp.sliubetskyi.core.model.modules.location.ILocationManager;

/**
 * Impl it if you wish to provide access to all app components.
 */
public interface IClientContext {
    IPersistentData getPersistentStorage();
    ILocationManager getLocationManager();
    IAppState getAppState();
    IPermissionsManager getPermissionsManager();
    IResourcesProvider getResProvider();
    IUITreadExecutor getUITreadExecutor();
}
