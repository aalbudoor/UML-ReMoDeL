/**
 * Tools for checking and compiling ReMoDeL models, metamodels and 
 * transformations.  Each of these programs consists of a single main
 * method, expecting a command-line argument denoting the path to the
 * input file (you can supply this using [Run -- Run Configurations] 
 * in Eclipse).
 * <p>
 * The order of development is:  create one or more Metamodel (.met) files 
 * first, check these using CheckMetamodel, and then compile these to Java 
 * packages using CompileMetamodel.  Next, create a Model instance (.mod) 
 * file that should conform to the source Metamodel just compiled, and check
 * it using CheckModel.  Finally, create a Transform (.tra) file referring
 * to the source and target Metamodels, check this using CheckTransform, and
 * then compile it to a Java package and class using CompileTransform.
 * <p>
 * Generated Transform classes have a main() method which allows them to be
 * executed.  They expect one (or more) pathnames to the input model files.
 * (If the Transform is a merging transformation, more than one input will 
 * be expected).
 * 
 * @author Anthony J H Simons
 * @version 3.1
 */
package home;