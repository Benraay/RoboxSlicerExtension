/*
 * This file is part of Robox Slicer Extension.
 *
 * Robox Slicer Extension is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Robox Slicer Extension is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Robox Slicer Extension.  If not, see <http://www.gnu.org/licenses/>.
 *
*/
package com.roboxing.slicerextension.control;

import java.io.File;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class Main {
    public static void main(String[] args) {

        System.out.println("Hello World!");
        String home = System.getProperty("user.home");
        String path = home + File.separator + "CEL Robox/PrintJobs/1cf3de2619db4321/1cf3de2619db4321.gcode.orig";
        try {

            PostProcessor postProcess = new PostProcessor(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
