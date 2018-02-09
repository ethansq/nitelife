package me.ethansq.nitelife.helpers;

public interface SimpleCallback {
    final static int SUCCESS = 1;
    final static int FAILURE = 2;
    final static int ERROR = 3;
    final static int NO_STATUS = 4;

    public void execute(final int STATUS);
}
