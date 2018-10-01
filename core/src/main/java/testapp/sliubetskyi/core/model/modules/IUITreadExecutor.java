package testapp.sliubetskyi.core.model.modules;

public interface IUITreadExecutor {
    /**
     * Executes runnable in UI Thread
     * @param runnable
     */
    void postUIThread(Runnable runnable);

    /**
     * Executes runnable in UI Thread with delay
     * @param runnable
     */
    void postUIThread(Runnable runnable, long delay);

    /**
     * Cancel runnable action
     * @param runnable
     */
    void cancelUIThreadAction(Runnable runnable);
}
