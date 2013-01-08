/*
 * Copyright (C) 2012 Julien Delacou <julien.delacou@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jde.grep;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grep {
	private static boolean h = false;
	private static boolean n = false;

	/**
	 * grep() - greps for a pattern in a given array of files
	 *
	 * @param s
	 *            - the pattern we are looking for
	 * @param files
	 *            - the list of files to be "greped" in
	 * @throws IOException
	 */
	public static void grep(String s, File... files) throws IOException {
		for (File f : files) {
			grep(s, f);
		}
	}

	/**
	 * grep() - greps for a pattern in a given file
	 *
	 * @param s
	 *            - the pattern we are looking for
	 * @param f
	 *            - the file to be "greped" in
	 * @throws IOException
	 */
	public static void grep(String s, File f) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(f));
		Pattern p = Pattern.compile(s);
		String l;
		Matcher m;
		int i = 1;

		while ((l = br.readLine()) != null) {
			StringBuilder sb = new StringBuilder();
			m = p.matcher(l);
			if (m.find()) {
				if (h)
					sb.append(f.getName() + ": ");
				if (n)
					sb.append(i + ": ");
				sb.append(l);
				System.out.println(sb.toString());
			}
			i++;
		}
	}

	/**
	 * lgrep() - greps for pattern in a given file and returns the result in a
	 * List of String
	 *
	 * @param s
	 *            - the pattern we are looking for
	 * @param f
	 *            - the file to be "greped" in
	 * @return a list of lines found
	 * @throws IOException
	 */
	public static List<String> lgrep(String s, File f) throws IOException {
		Pattern p = Pattern.compile(s);
		String l;
		Matcher m;
		int i = 1;
		List<String> r = new ArrayList<String>();

		BufferedReader br = new BufferedReader(new FileReader(f));
		while ((l = br.readLine()) != null) {
			StringBuilder sb = new StringBuilder();
			m = p.matcher(l);
			if (m.find()) {
				if (h)
					sb.append(f.getName() + ": ");
				if (n)
					sb.append(i + ": ");
				sb.append(l);
				r.add(sb.toString());
			}
			i++;
		}
		br.close();

		return r;
	}

	/**
	 * setOptions() - sets options before grep
	 *
	 * @param n
	 *            - shows the line number
	 * @param f
	 *            - shows the file containing pattern
	 */
	public static void setOptions(boolean n, boolean h) {
		Grep.n = n;
		Grep.h = h;
	}

	/* example of use */
	public static void main(String... args) {
		if (args.length < 2) {
			System.err.println("usage: grep <pattern> <file(s)>");
			System.exit(-1);
		}

		File f = new File(args[args.length - 1]);
		setOptions(false, true);
		try {
			grep(args[args.length - 2], f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
