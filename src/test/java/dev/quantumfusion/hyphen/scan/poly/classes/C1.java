package dev.quantumfusion.hyphen.scan.poly.classes;

import dev.quantumfusion.hyphen.annotation.Serialize;

public class C1<A> extends C0 {
	@Serialize
	public A a;

	public C1(A a) {
		this.a = a;
	}
}