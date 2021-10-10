package dev.quantumfusion.hyphen.scan.poly.classes;

import dev.quantumfusion.hyphen.annotation.Serialize;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class CoWrappedC1<A, CA extends C1<A>> extends C1<CA> {
	@Serialize
	public A selfA;

	public CoWrappedC1(CA ca, A selfA) {
		super(ca);
		this.selfA = selfA;
	}

	public static <A, CA extends C1<A>> Stream<? extends CoWrappedC1<A, CA>> generate(
			Supplier<? extends Stream<? extends A>> aSupplier,
			Supplier<? extends Stream<? extends CA>> caSupplier
	) {
		return caSupplier.get().flatMap(ca ->
				aSupplier.get().map(selfA -> new CoWrappedC1<>(ca, selfA))
		);
	}
}
