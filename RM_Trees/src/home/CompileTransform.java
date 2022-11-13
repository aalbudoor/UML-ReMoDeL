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

import remodel.io.MasterCompiler;
import remodel.io.TransformReader;
import remodel.meta.Metamodel;
import remodel.meta.Transform;

/**
 * CompileTransform is a ReMoDeL tool that compiles a ReMoDeL transformation
 * into a Java package. It expects a single command-line argument, an input
 * path of the form "dirname/FileName.tra", giving the directory prefix and
 * the name of the transformation file.  If the input file is a syntactically
 * correct transformation, create the Java package "rule.ruleset", where the
 * ruleset is the declared ruleset to which the transformation belongs, and
 * creates a class for the transformation.  For convenience, recompiles the 
 * metamodels on which the transformation depends.
 * 
 * @author Anthony J H Simons
 * @version 1.0
 */
public class CompileTransform {

	/**
	 * Reads a ReMoDeL transformation and compiles a Java package.
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
		
		// The input file to read
		File inFile = new File(inPath);

		try (TransformReader reader = new TransformReader(inFile);
				MasterCompiler compiler = new MasterCompiler()) {

			Transform transform = reader.readModel();
			System.out.println("Successfully read " + inFile);
			
			for (Metamodel metamodel : transform.getMetamodels()) {
				String metaPath = prefix + metamodel.getName() + ".met";
				File metaFile = new File(metaPath);
				compiler.compileModel(metamodel);
				System.out.println("Successfully recompiled " + metaFile);
			}
			compiler.compileModel(transform);
			System.out.println("Successfully compiled " + inFile);
		}
	}

}
