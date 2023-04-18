package io.profanesuite.shared.config;

/*
 * MIT License
 *
 * Copyright (c) 2022 ProfaneSuite
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

public class HeaderRegexTest {

  @Test
  void willValidateSource() throws IOException, URISyntaxException {
    final String[] headerRegEx = headerRegex();
    final String[] validSource = validSource();

    SoftAssertions.assertSoftly(
        softly -> {
          IntStream.range(0, headerRegEx.length)
              .forEach(
                  regexIndex -> {
                    Assertions.assertThat(validSource)
                        .describedAs(
                            "Size can not be checked because regex header is larger than source")
                        .hasSizeGreaterThan(regexIndex);
                    softly.assertThat(validSource[regexIndex]).matches(headerRegEx[regexIndex]);
                  });
        });
  }

  private String[] validSource() throws URISyntaxException, IOException {
    final URI uri =
        Objects.requireNonNull(getClass().getClassLoader().getResource("valid/Valid.java")).toURI();
    Path path = Paths.get(uri);
    return Files.readAllLines(path, StandardCharsets.UTF_8).toArray(String[]::new);
  }

  private String[] headerRegex() throws URISyntaxException, IOException {
    final URI uri =
        Objects.requireNonNull(getClass().getClassLoader().getResource("config/header.txt"))
            .toURI();

    Path path = Paths.get(uri);
    return Files.readAllLines(path, StandardCharsets.UTF_8).toArray(String[]::new);
  }
}
