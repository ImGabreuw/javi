package io.github.imgabreuw.usecases;

public interface UseCase<I extends UseCase.InputValues, O extends UseCase.OutputValues> {

    interface InputValues {
    }

    interface OutputValues {
    }

    O execute(I input);

}
