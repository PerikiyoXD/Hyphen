package dev.quantumfusion.hyphen.scan.type;

import dev.quantumfusion.hyphen.scan.Clazzifier;
import dev.quantumfusion.hyphen.scan.Direction;
import dev.quantumfusion.hyphen.util.ScanUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedArrayType;
import java.lang.reflect.AnnotatedType;
import java.util.Objects;

public class ArrayClazz extends Clazz {
	public final Clazz component;

	public ArrayClazz(@NotNull Class<?> aClass, Annotation[] sourceAnnotations, Annotation[] annotations, Clazz component) {
		super(aClass, sourceAnnotations, annotations);
		this.component = component;
	}

	public static ArrayClazz create(AnnotatedType array, @Nullable Clazz ctx, Direction dir) {
		return new ArrayClazz(Object[].class, array.getAnnotations(), ScanUtil.parseAnnotations(ctx), Clazzifier.create(((AnnotatedArrayType) array).getAnnotatedGenericComponentType(), ctx, dir));
	}

	@Override
	public int defined() {
		return 1 + component.defined();
	}

	@Override
	public String toString() {
		return component.toString() + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		ArrayClazz that = (ArrayClazz) o;
		return Objects.equals(component, that.component);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), component);
	}
}
