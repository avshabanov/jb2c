package com.truward.jb2c.mid;

import javax.annotation.Nonnull;

/**
 * @author Alexander Shabanov
 */
public final class Types {

  interface Visitor<R> {
    R visitNode(@Nonnull Node node);

    default R visitPrimitiveType(@Nonnull PrimitiveType node) {
      return visitNode(node);
    }
  }

  interface Node {
    <R> R apply(@Nonnull Visitor<R> visitor);
  }

  interface Type extends Node {
    default String getName() {
      return "Unknown";
    }
  }

  enum PrimitiveTypeKind {
    BOOLEAN,
    BYTE,
    CHAR,
    SHORT,
    INT,
    LONG,
    DOUBLE
  }

  interface PrimitiveType extends Type {
    @Nonnull
    PrimitiveTypeKind getKind();

    default @Override <R> R apply(@Nonnull Visitor<R> visitor) {
      return visitor.visitPrimitiveType(this);
    }
  }
}
