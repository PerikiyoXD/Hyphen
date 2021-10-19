package dev.quantumfusion.hyphen.codegen.def;

import dev.quantumfusion.hyphen.SerializerHandler;
import dev.quantumfusion.hyphen.codegen.MethodHandler;
import dev.quantumfusion.hyphen.scan.FieldEntry;
import dev.quantumfusion.hyphen.scan.type.Clazz;
import dev.quantumfusion.hyphen.thr.HyphenException;
import dev.quantumfusion.hyphen.util.Style;
import dev.quantumfusion.hyphen.util.GenUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.objectweb.asm.Opcodes.*;

public class ClassDef extends MethodDef {
	protected final Map<FieldEntry, SerializerDef> fields = new LinkedHashMap<>();
	protected final Class<?> aClass;

	public ClassDef(SerializerHandler<?, ?> handler, Clazz clazz) {
		super(handler.codegenHandler, clazz);
		this.aClass = clazz.getDefinedClass();
		try {
			for (FieldEntry field : clazz.getFields()) {
				try {
					fields.put(field, handler.acquireDef(field.clazz()));
				} catch (Throwable throwable) {
					throw HyphenException.thr("field", Style.LINE_RIGHT, field, throwable);
				}
			}
		} catch (Throwable throwable) {
			throw HyphenException.thr("class", Style.LINE_DOWN, clazz, throwable);
		}
	}

	@Override
	public void writeMethodGet(MethodHandler mh) {
		mh.typeOp(NEW, aClass);
		mh.op(DUP);
		List<Class<?>> constParameters = new ArrayList<>();
		fields.forEach((fieldEntry, def) -> {
			def.writeGet(mh);
			// TODO fix constructors
			constParameters.add(fieldEntry.clazz().getBytecodeClass());
		});
		//TODO add generic support
		mh.callInst(INVOKESPECIAL, aClass, "<init>", Void.TYPE, constParameters.toArray(Class[]::new));
	}

	@Override
	public void writeMethodPut(MethodHandler mh, Runnable valueLoad) {
		fields.forEach((fieldEntry, def) -> def.writePut(mh, () -> {
			//TODO add get method support / generic support
			allocateField(mh, fieldEntry.field(), fieldEntry.clazz(), valueLoad);
		}));
	}

	@Override
	public void writeMethodMeasure(MethodHandler mh, Runnable valueLoad) {
		if (fields.size() == 0) {
			mh.op(ICONST_0);
		} else {
			int i = 0;
			for (var entry : fields.entrySet()) {
				var field = entry.getKey().field();
				entry.getValue().writeMeasure(mh, () -> allocateField(mh, field, entry.getKey().clazz(), valueLoad));
				if (i++ != 0) mh.op(IADD);
			}
		}
	}

	private void allocateField(MethodHandler mh, Field field, Clazz clazz, Runnable dataLoad) {
		dataLoad.run();
		if (Modifier.isPublic(field.getModifiers())) {
			mh.visitFieldInsn(GETFIELD, aClass, field.getName(), field.getType());
		} else {
			mh.callInst(INVOKEVIRTUAL, aClass, getGetter(aClass, field.getName()), clazz.getBytecodeClass());
		}
		GenUtil.shouldCastGeneric(mh, clazz);
	}

	public String getGetter(Class<?> aClass, String fieldName) {
		if (!aClass.isRecord()) {
			try {
				final String name = "get" + GenUtil.upperCase(fieldName);
				aClass.getDeclaredMethod(name);
				return name;
			} catch (NoSuchMethodException ignored) {
			}
		}
		try {
			aClass.getDeclaredMethod(fieldName);
			return fieldName;
		} catch (NoSuchMethodException ignored) {
		}
		throw new RuntimeException("Could not access" + fieldName + " in class " + aClass.getSimpleName());
	}

}
