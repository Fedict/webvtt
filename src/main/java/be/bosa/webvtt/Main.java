/*
 * Copyright (c) 2020, Bart Hanssens <bart.hanssens@bosa.fgov.be>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package be.bosa.webvtt;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Converts text file to VTT subtitle file
 * 
 * @author Bart.Hanssens
 */
public class Main {
	public static void main(String[] args) throws IOException {
		if (args.length < 3) {
			System.out.println("Usage: infile outfile milliseconds");
			System.exit(-1);
		}
		String inFile = args[0];
		String outFile = args[1];
		String duration = args[2];

		try (BufferedWriter w = Files.newBufferedWriter(Path.of(outFile))) {
			Duration d = Duration.ofMillis(Integer.valueOf(duration));
			
			LocalTime timer = LocalTime.MIN;
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
			
			w.append("WEBVTT");
			w.newLine();
			w.newLine();

			for (String l: Files.readAllLines(Path.of(inFile))) {
				if (l.isBlank()) {
					continue;
				}
				String s = timer.format(fmt);
				timer = timer.plus(d);
				String e = timer.format(fmt);
				
				w.append(s).append(" --> ").append(e);
				w.newLine();
				w.append(l);
				w.newLine();
				w.newLine();
			}
		}
	}
}
