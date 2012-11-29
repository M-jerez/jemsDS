package com.jems.persistence.accessors;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Hashtable;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtNewMethod;

import com.jems.persistence.PropertyAccessor;


@SuppressWarnings("rawtypes")
public class AccessorsFactory {	
	
	private static final Hashtable<String, PropertyAccessor> accessors = new Hashtable<String, PropertyAccessor>(40);
	
	public static boolean enable = false;

	public static PropertyAccessor getAccessor(Class<?> klazz,Field field) throws Exception{
		//TimeProfiler tp = BenchmarkRunner.getTimeProfiler("Accesors Profiling");
		
		klazz.getDeclaredField(field.getName());
		String key = makekey(klazz, field);
		
		//tp.startTime(key);
		if(accessors.containsKey(key))
			return accessors.get(key);
		PropertyAccessor acc  = createAccessor(klazz, field);
		accessors.put(key, acc);
		//tp.stopTime(key);
		
		return acc;
	}	
	
	private static String makekey(Class<?> klazz,Field field){
		return klazz.getName()+"."+field.getName();
	}

	private static PropertyAccessor createAccessor(Class<?> klazz, Field field) throws Exception { 	
		if(!enable) return new DefaultAccesor();
		if(Modifier.isPrivate(field.getModifiers())) return new DefaultAccesor();
		
		
		final String fieldType = field.getType().getCanonicalName();
		final String klazzName = klazz.getCanonicalName();
		final String fieldName = field.getName();	
		
		
		final String classTemplate = "%s_%s_accessor";
		String getTemplate = null;
		String setTemplate = null;
		
		
		getTemplate = String.format(
				"public Object get(java.lang.reflect.Field f, Object source){"+ 
				"return ((%s)source).%s;}", 
				klazzName, 
				fieldName);
		setTemplate = String.format(
				"public void set(java.lang.reflect.Field f, Object source, Object value){"+ 
				" ((%s)source).%s = (%s) value;}", 
				klazzName,
				fieldName, fieldType);	
			
		
		final String getMethod = getTemplate;
		final String setMethod = setTemplate;
		final String className = String.format(	classTemplate, klazzName,fieldName);
		
		ClassPool pool = ClassPool.getDefault();
		CtClass ctClass = pool.makeClass(className);
		ctClass.addMethod(CtNewMethod.make(getMethod, ctClass));
		ctClass.addMethod(CtNewMethod.make(setMethod, ctClass));
		ctClass.setInterfaces(new CtClass[] { pool.get(PropertyAccessor.class.getName()) });
		Class<?> generated = ctClass.toClass();
		//TimeBenchmark.bench.stopTime("CreateAccessor "+klazzName+"."+fieldName);
		return (PropertyAccessor) generated.newInstance();				
	}

}
