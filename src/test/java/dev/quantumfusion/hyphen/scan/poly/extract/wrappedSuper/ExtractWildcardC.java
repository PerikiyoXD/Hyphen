package dev.quantumfusion.hyphen.scan.poly.extract.wrappedSuper;

import dev.quantumfusion.hyphen.FailTest;
import dev.quantumfusion.hyphen.scan.annotations.Data;
import dev.quantumfusion.hyphen.scan.annotations.DataSubclasses;
import dev.quantumfusion.hyphen.scan.poly.classes.C1;
import dev.quantumfusion.hyphen.scan.poly.classes.CoWrappedC1Super;
import dev.quantumfusion.hyphen.util.TestThis;

@Data
@TestThis
@FailTest(/*NotYetImplementedException.class*/)
public class ExtractWildcardC {
	@DataSubclasses({C1.class, CoWrappedC1Super.class})
	public C1<? extends C1<Integer>> data;

	public ExtractWildcardC(C1<? extends C1<Integer>> data) {
		this.data = data;
	}
}
