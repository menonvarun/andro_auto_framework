package org.imaginea.botbot;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static org.objectweb.asm.Opcodes.ICONST_0;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.IRETURN;
import static org.objectweb.asm.Opcodes.ASM4;

public class CustomVisitor extends ClassVisitor {
	private boolean extendsActivity = false;
	public boolean isMethodPresent=false;

	public CustomVisitor() {
		super(ASM4);
	}

	public CustomVisitor(ClassWriter cw) {
		super(ASM4, cw);
	}

	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		extendsActivity = false;
		if (superName.contentEquals("android/app/Activity")) {
			System.out.println(name + " extends " + superName + " {");
			extendsActivity = true;
		}
	}

	public void visitSource(String source, String debug) {
	}

	public void visitOuterClass(String owner, String name, String desc) {
	}

	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		return null;
	}

	public void visitAttribute(Attribute attr) {
	}

	public void visitInnerClass(String name, String outerName,
			String innerName, int access) {
	}

	public FieldVisitor visitField(int access, String name, String desc,
			String signature, Object value) {
		// System.out.println("    " + desc + " " + name);
		return null;
	}

	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		if (extendsActivity) {
			System.out
					.println("    " + "name is:" + name + " desc is: " + desc);
			if (name.equals("onKeyDown")
					&& desc.equals("(ILandroid/view/KeyEvent;)Z")) {
				isMethodPresent = true;
			}

		}
		return cv.visitMethod(access, name, desc, signature, exceptions);
	}

	public void visitEnd() {
		if (!isMethodPresent) {
			MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "onKeyDown",
					"(ILandroid/view/KeyEvent;)Z", null, null);
			mv.visitCode();
			mv.visitInsn(ICONST_0);
			mv.visitInsn(IRETURN);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
		}
		cv.visitEnd();
	}
}
