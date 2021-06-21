/*
 * blanco Framework
 * Copyright (C) 2004-2017 IGA Tosiki
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
/*
 * Copyright 2017 Toshiki Iga
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package blanco.cg.transformer.python;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import blanco.cg.transformer.AbstractBlancoCgPythonStyleTransformer;
import blanco.cg.valueobject.BlancoCgSourceFile;

/**
 * An entry point of the transformer that auto-generates source code from blancoCg value objects.
 * 
 * It is recommended to generate them via BlancoCgTransformerFactory.<br>
 * This transformer converts a value object into Python source code.
 * 
 * @author IGA Tosiki
 */
public class BlancoCgPythonSourceTransformer extends
        AbstractBlancoCgPythonStyleTransformer {

    /**
     * Converts the source file value object to Python source code and outputs it to the writer.
     * 
     * Does not consider the package structure to be a directory structure in this API. In this process, it just outputs to the writer.
     * 
     * @param argSourceFile
     *            A source file value object.
     * @param argWriter
     *             A writer for output.
     * @throws IOException If an I/O exception occurs.
     */
    public void transform(final BlancoCgSourceFile argSourceFile,
            final BufferedWriter argWriter) throws IOException {
        if (argSourceFile == null) {
            throw new IllegalArgumentException("A source file was given as a null value. Aborts the process.");
        }
        if (argWriter == null) {
            throw new IllegalArgumentException("A writer for output was given as a null value. Aborts the process.");
        }

        final List<java.lang.String> sourceLines = new BlancoCgSourceFilePythonSourceExpander()
                .transformSourceFile(argSourceFile);

        // Formats the source code.
        formatSource(sourceLines);

        // Outputs the source code to the writer.
        source2Writer(sourceLines, argWriter);

        // Performs a flush to be sure.
        argWriter.flush();
    }

    /**
     * Gets the extension to be attached to the source code.
     * 
     * @return An extension to attach to the source code.
     */
    protected String getSourceFileExt() {
        return ".py";
    }
}
