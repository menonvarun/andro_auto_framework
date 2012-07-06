package org.imaginea.botbot;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static org.objectweb.asm.Opcodes.*;
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
			//System.out.println(name + " extends " + superName + " {");
			extendsActivity = true;
		}
		cv.visit(version, access, name, signature, superName, interfaces);
	}

	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		if (extendsActivity) {
			//System.out
				//	.println("    " + "name is:" + name + " desc is: " + desc);
			if (name.equals("onKeyDown")
					&& desc.equals("(ILandroid/view/KeyEvent;)Z")) {
				isMethodPresent = true;
				
			}
			//System.out.println("method not present");
		}
		
		return cv.visitMethod(access, name, desc, signature, exceptions);
	}

	public void visitEnd() {
		if (extendsActivity && !isMethodPresent) {
			System.out.println("Adding method");
			MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, "onKeyDown",
					"(ILandroid/view/KeyEvent;)Z", null, null);
			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ILOAD, 1);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitMethodInsn(INVOKESPECIAL, "android/app/Activity", "onKeyDown", "(ILandroid/view/KeyEvent;)Z");
			mv.visitInsn(IRETURN);
			mv.visitMaxs(3, 3);
			mv.visitEnd();
		}
		cv.visitEnd();
	}
}
