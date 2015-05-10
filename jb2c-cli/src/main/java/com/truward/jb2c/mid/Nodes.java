package com.truward.jb2c.mid;

import com.truward.jb2c.backend.CodeGen;
import com.truward.jb2c.model.PrimitiveTypeKind;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Shabanov
 */
public final class Nodes {
  private Nodes() {}

  interface Visitor<R> {
    default R visitNode(@Nonnull Node node) {
      throw new UnsupportedOperationException("Unsupported node: " + node);
    }

    default R visitPrimitiveType(@Nonnull PrimitiveType node) { return visitNode(node); }
    default R visitPackage(@Nonnull Package node) { return visitNode(node); }
    default R visitClassType(@Nonnull ClassType node) { return visitNode(node); }
    default R visitAnnotationExpression(@Nonnull AnnotationExpression node) { return visitNode(node); }
    default R visitVar(@Nonnull Var node) { return visitNode(node); }
    default R visitMethod(@Nonnull Method node) { return visitNode(node); }
    default R visitBlock(@Nonnull Block node) { return visitNode(node); };
  }

  public static abstract class Node {
    public abstract <R> R apply(@Nonnull Visitor<R> v);
  }

  public static abstract class PrimitiveType extends Node {
    public final PrimitiveTypeKind kind;

    public PrimitiveType(PrimitiveTypeKind kind) {
      this.kind = kind;
    }

    @Override public <R> R apply(@Nonnull Visitor<R> v) { return v.visitPrimitiveType(this); }
  }

  public static final class Package extends Node {
    public String name;

    @Override public <R> R apply(@Nonnull Visitor<R> v) { return v.visitPackage(this); }
  }

  public static final class ClassType extends Statement {
    public Node parent;
    public String name;
    public Node superclass;
    public List<ClassType> interfaces = new ArrayList<>();
    public List<Node> members = new ArrayList<>();

    public CodeGen.Class classCodeGen = new CodeGen.Class(); // helper for code generation

    @Override public <R> R apply(@Nonnull Visitor<R> v) { return v.visitClassType(this); }
  }

  //
  // Expressions
  //

  public static abstract class Expression extends Node {
  }

  public static final class AnnotationExpression extends Expression {
    public Node annotationType;
    public List<Expression> arguments = new ArrayList<>();

    @Override public <R> R apply(@Nonnull Visitor<R> v) { return v.visitAnnotationExpression(this); }
  }

  //
  // Statements
  //

  public static abstract class Statement extends Node {
    public long accessFlags;
    public List<AnnotationExpression> annotations = new ArrayList<>();
  }

  public static final class Var extends Statement {
    public Node type;
    public String name;

    @Override public <R> R apply(@Nonnull Visitor<R> v) { return v.visitVar(this); }
  }

  public static final class Block extends Statement {
    public List<Statement> statements = new ArrayList<>();

    @Override public <R> R apply(@Nonnull Visitor<R> v) { return v.visitBlock(this); }
  }

  public static final class Method extends Statement {
    public Node returnType;
    public String name;
    public List<Var> parameters = new ArrayList<>();
    public Block block;

    public CodeGen.Method methodCodeGen = new CodeGen.Method();

    @Override public <R> R apply(@Nonnull Visitor<R> v) { return v.visitMethod(this); }
  }
}
