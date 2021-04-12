package view;

import javafx.concurrent.Task;

public class test extends Task<Long> {

    private int n;

    public test(int n) {
        this.n = n;
    }

    @Override
    protected Long call() throws Exception {
        updateMessage("    Processing... ");
        long result = fibonacci(n);
        updateMessage("    Done.  ");
        return result;
    }

    public long fibonacci(long number) {
        if (number == 0 || number == 1)
            return number;
        else
            return fibonacci(number - 1) + fibonacci(number - 2);
    }
}


