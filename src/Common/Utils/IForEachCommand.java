package Common.Utils;

/**
 * Created by adam on 14.01.17.
 */
public interface IForEachCommand<T, O>
{
    void Do(T object, int i, int j, O out);
}
