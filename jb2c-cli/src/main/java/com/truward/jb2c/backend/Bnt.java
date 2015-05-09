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
  }

  public static abstract class Node {
    abstract <R> R apply(@Nonnull Visitor<R> v);
  }

  public static abstract class Type extends Node {
  }

  public static final class Var extends Node {
    @Override <R> R apply(@Nonnull Visitor<R> v) { return v.visitVar(this); }
  }

  public static final class Package extends Node {
    public final Package parent;
    public final String name;

    public Package(Package parent, String name) {
      this.parent = parent;
      this.name = name;
    }

    @Override <R> R apply(@Nonnull Visitor<R> v) {
      return v.visitPackage(this);
    }
  }

  public static final class ClassType extends Type {
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

  public static final class PrimitiveType extends Type {
    final PrimitiveTypeKind kind;

    public PrimitiveType(PrimitiveTypeKind kind) {
      this.kind = kind;
    }

    @Override <R> R apply(@Nonnull Visitor<R> v) { return v.visitPrimitiveType(this); }
  }
}
