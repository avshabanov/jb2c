package com.truward.jb2c.backend;

import com.truward.jb2c.model.PrimitiveTypeKind;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Shabanov
 */
public class Bnt {
  interface Visitor<R> {
    default R visitNode(@Nonnull Node node) {
      throw new UnsupportedOperationException("Unsupported node: " + node);
    }

    default R visitPrimitiveType(@Nonnull PrimitiveType node) { return visitNode(node); }
    default R visitPackage(@Nonnull Package node) { return visitNode(node); }
    default R visitClassType(@Nonnull ClassType node) { return visitNode(node); }
    default R visitVar(@Nonnull Var node) { return visitNode(node); }
    default R visitMethod(@Nonnull Method node) { return visitNode(node); }
  }

  public interface Node {
    <R> R apply(@Nonnull Visitor<R> v);
  }

  public interface Type extends Node {
  }

  public static final class Var implements Node {
    @Override public <R> R apply(@Nonnull Visitor<R> v) { return v.visitVar(this); }
  }

  public static final class Package implements Node {
    public final Package parent;
    public final String name;

    public Package(Package parent, String name) {
      this.parent = parent;
      this.name = name;
    }

    @Override public <R> R apply(@Nonnull Visitor<R> v) {
      return v.visitPackage(this);
    }
  }

  public static final class ClassType implements Type {
    public final Node parent;
    public final String name;

    public ClassType superclass;
    public List<ClassType> interfaces = new ArrayList<>();
    public List<Node> members = new ArrayList<>();

    public String fullName;
    public String structName;
    public boolean isInterface;

    public ClassType(Node parent, String name) {
      this.parent = parent;
      this.name = name;
    }

    public <R> R apply(@Nonnull Visitor<R> visitor) { return visitor.visitClassType(this); }
  }

  public static final class PrimitiveType implements Type {
    final PrimitiveTypeKind kind;

    public PrimitiveType(PrimitiveTypeKind kind) {
      this.kind = kind;
    }

    @Override public <R> R apply(@Nonnull Visitor<R> v) { return v.visitPrimitiveType(this); }
  }

  public static final class Method implements Node {
    @Override public <R> R apply(@Nonnull Visitor<R> v) { return v.visitMethod(this); }
  }
}
