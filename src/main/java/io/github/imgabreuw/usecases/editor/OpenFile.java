package io.github.imgabreuw.usecases.editor;

import io.github.imgabreuw.usecases.UseCase;
import lombok.SneakyThrows;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OpenFile implements UseCase<OpenFile.InputValues, OpenFile.OutputValues> {

    @SneakyThrows
    @Override
    public OutputValues execute(InputValues input) {
        var args = input.args();

        if (args.length != 1) {
            throw new InputMismatchException("File name expected.");
        }

        String filename = args[0];
        Path path = Path.of(filename);

        if (!Files.exists(path)) {
            throw new FileNotFoundException("File not found.");
        }

        try (Stream<String> lines = Files.lines(path)) {
            return new OutputValues(lines.collect(Collectors.toList()));
        } catch (IOException e) {
            throw new FileSystemException(filename, e.getMessage(), e.getCause().getMessage());
        }
    }

    public record InputValues(String[] args) implements UseCase.InputValues {
    }

    public record OutputValues(List<String> content) implements UseCase.OutputValues {
    }

}
