/**
 * ReMoDeL (Reusable Model Design Languages) tools for developing
 * and compiling metamodels, models and model transformations.
 * 
 * Copyright (c) 2020-2021 Anthony J H Simons, Department of Computer
 * Science, University of Sheffield, UK.  All rights reserved.
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * 
 * This software is proprietary software, whose IP is owned by
 * Anthony J H Simons and the University of Sheffield.  All use of
 * this software must be covered by a license drawn up with the
 * owners.
 *
 * Please contact the Department of Computer Science, University of
 * Sheffield, Regent Court, 211 Portobello, Sheffield S1 4DP, UK or
 * visit www.sheffield.ac.uk/dcs if you need additional information 
 * or have any questions.
 */
package home;

import java.io.File;
import java.io.IOException;

import remodel.io.TransformReader;
import remodel.io.TransformWriter;
import remodel.meta.Transform;

/**
 * CheckTransform is a ReMoDeL tool that checks the syntax of a ReMoDeL 
 * transformation.  It expects a single command-line argument, an input path
 * of the form "dirname/FileName.tra", giving the directory prefix and the
 * name of the transformation file.  If the input file is a syntactically
 * correct transformation, rewrites this transformation to "dirname/Out.tra"
 * for comparison.
 * 
 * @author Anthony J H Simons
 * @version 1.0
 */
public class CheckTransform {
	
	/**
	 * Reads a transformation file and outputs a copy of this file.
	 * @param args args[0] is the input pathname.
	 * @throws IllegalArgumentException if the command-line argument is
	 * missing or incorrect.
	 * @throws IOException if the expected input syntax is faulty.
	 */
	public static void main(String[] args) throws 
	IOException, IllegalArgumentException {
		
		// Process the command line argument.
		if (args.length == 0)
			throw new IllegalArgumentException(
					"Missing command-line argument for input path.");
		String inPath = args[0];
		int pos = inPath.lastIndexOf('/');
		if (pos == -1)
			throw new IllegalArgumentException (
					"Missing directory prefix part of the input path.");
		String prefix = inPath.substring(0, pos+1);  // include slash
		String outPath = prefix + "Out.tra";
		
		// The input file to read
		File inFile = new File(inPath);
		
		// The output file to write
		File outFile = new File(outPath);
		
		// Try-with-resources block
		try (TransformReader reader = new TransformReader(inFile);
				TransformWriter writer = new TransformWriter(outFile)) {
			
			Transform transform = reader.readModel();
			System.out.println("Successfully read " + inFile);
			
			 writer.writeModel(transform);
			 System.out.println("Successfully wrote " + outFile);
		}
	}

}
