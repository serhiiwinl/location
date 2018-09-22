package testapp.sliubetskyi.location.core.state;

import testapp.sliubetskyi.location.core.data.IPersistentData;

/**
 * Impl it if you wish to provide access to all app components.
 */
public interface IClientContext {
    IPersistentData getPersistentStorage();
    AppState getAppState();
}
