package test.inf;

/**
 *
 * @param <T> the result type of method {@code doNodeTaskWork}
 */
public interface INodeTaskWork {
    /**
     * execute a nodeTask with a result, or throws an exception if unable to do so.
     *
     * @return nodeTask exec result
     * @throws Exception if unable to compute a result
     */
    boolean doNodeTaskWork() throws Exception;
}
