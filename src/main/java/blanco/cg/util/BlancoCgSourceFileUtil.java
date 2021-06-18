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
package blanco.cg.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import blanco.cg.resourcebundle.BlancoCgResourceBundle;

/**
 * Utility class for the source file of blancoCg.
 * 
 * @author IGA Tosiki
 */
public class BlancoCgSourceFileUtil {
    /**
     * A class for handling resource bundle messages.
     */
    protected static final BlancoCgResourceBundle fBundle = new BlancoCgResourceBundle();

    /**
     * Gets the default file comments.
     * 
     * <UL>
     * <LI>If there is a relative path meta/program/fileheader.txt, this will be used preferentially.
     * (It must be written in UTF-8)
     * <LI>Uses the resource bundle specification.
     * </UL>
     * 
     * @return An array of default file comments.
     */
    public static List<String> getDefaultFileComment() {
        final List<String> result = new ArrayList<String>();
        try {
            final File file = new File(fBundle.getFileHeaderPath());
            if (file.exists() == true && file.isFile() && file.canRead()) {
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(
                            new FileInputStream(file), "UTF-8"));
                    for (;;) {
                        final String line = reader.readLine();
                        if (line == null) {
                            break;
                        }
                        result.add(line);
                    }
                } finally {
                    if (reader != null) {
                        reader.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result.size() == 0) {
            result.add(fBundle.getDefaultFileComment());
        }

        return result;
    }
}
