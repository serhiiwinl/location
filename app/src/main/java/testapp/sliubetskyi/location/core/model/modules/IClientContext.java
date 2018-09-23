package testapp.sliubetskyi.location.core.model.modules;

import testapp.sliubetskyi.location.core.model.AppState;

/**
 * Impl it if you wish to provide access to all app components.
 */
public interface IClientContext {
    IPersistentData getPersistentStorage();
    ILocationManager getLocationManager();
    AppState getAppState();
    IPermissions getPermissions();
}
