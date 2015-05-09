package com.truward.jb2c.mid;

import com.sun.corba.se.spi.orbutil.fsm.State;
import com.truward.jb2c.model.PrimitiveTypeKind;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Alexander Shabanov
 */
public final class Types {

  interface Visitor<R> {
    default R visitNode(@Nonnull Node node) {
      throw new UnsupportedOperationException("Unsupported node: " + node);
    }

    default R visitPrimitiveType(@Nonnull PrimitiveType node) { return visitNode(node); }
    default R visitClassType(@Nonnull ClassType node) { return visitNode(node); }
  }

  interface Node {
    <R> R apply(@Nonnull Visitor<R> v);
  }

  interface Type extends Node {
  }

  interface PrimitiveType extends Type {
    @Nonnull PrimitiveTypeKind getKind();

    default @Override <R> R apply(@Nonnull Visitor<R> v) { return v.visitPrimitiveType(this); }
  }

  interface ClassType extends Type {
    default @Override <R> R apply(@Nonnull Visitor<R> v) { return v.visitClassType(this); }
  }

  //
  // Expressions
  //

  interface Expression extends Node {
  }

  interface AnnotationExpression extends Expression {
    @Nonnull Type getAnnotationType();

    @Nonnull List<Expression> getArguments();
  }

  //
  // Statements
  //

  interface Statement extends Node {
    long getAccessFlags();

    @Nonnull List<AnnotationExpression> getAnnotations();
  }

  // TODO: util:
  static boolean isPublic(@Nonnull Statement statement) {
    return (statement.getAccessFlags() & 0x1) != 0;
  }

  interface Var extends Statement {
    @Nonnull Type getType();

    @Nonnull String getName();
  }

  interface Method extends Statement {
    @Nonnull Type getReturnType();

    @Nonnull String getName();

    @Nonnull List<Var> getParameters();
  }
}
