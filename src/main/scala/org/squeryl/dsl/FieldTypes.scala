package org.squeryl.dsl

import ast._
import org.squeryl.{Query}

trait FieldTypes {
  self: TypeArithmetic =>
  

  type ByteType

  type IntType

  type StringType

  type FloatType

  type DoubleType

  type LongType

  type BooleanType

  type DateType

  trait NumericalExpression[A] extends TypedExpressionNode[A] {

    def +[B](b: NumericalExpression[B]) = new BinaryAMSOp[A,B](this, b, "+")
    def *[B](b: NumericalExpression[B]) = new BinaryAMSOp[A,B](this, b, "*")
    def -[B](b: NumericalExpression[B]) = new BinaryAMSOp[A,B](this, b, "-")
    def /[B](b: NumericalExpression[B]) = new BinaryDivOp[A,B](this, b, "/")

    def ===[B](b: NumericalExpression[B]) = new BinaryOperatorNodeLogicalBoolean(this, b, "=")
    def <>[B](b: NumericalExpression[B]) = new BinaryOperatorNodeLogicalBoolean(this, b, "<>")
    def > [B](b: NumericalExpression[B]) = new BinaryOperatorNodeLogicalBoolean(this, b, ">")
    def >=[B](b: NumericalExpression[B]) = new BinaryOperatorNodeLogicalBoolean(this, b, ">=")
    def < [B](b: NumericalExpression[B]) = new BinaryOperatorNodeLogicalBoolean(this, b, "<")
    def <=[B](b: NumericalExpression[B]) = new BinaryOperatorNodeLogicalBoolean(this, b, "<=")

    def ||[B](e: TypedExpressionNode[B]) = new ConcatOp(this,e)

    def isNull = new PostfixOperatorNode("is null", this) with LogicalBoolean

    def isNotNull = new PostfixOperatorNode("is not null", this) with LogicalBoolean

    def in[B <% NumericalExpression[_]](e: Query[B]) = new BinaryOperatorNodeLogicalBoolean(this, e.ast, "in")

    def in(l: ListNumerical) = new BinaryOperatorNodeLogicalBoolean(this, l, "in")

    def ~ = this
  }

  trait NonNumericalExpression[A] extends TypedExpressionNode[A] {

    def ===[A](b: NonNumericalExpression[A]) = new BinaryOperatorNodeLogicalBoolean(this, b, "=")
    def <>[A](b: NonNumericalExpression[A]) = new BinaryOperatorNodeLogicalBoolean(this, b, "<>")
    def > [A](b: NonNumericalExpression[A]) = new BinaryOperatorNodeLogicalBoolean(this, b, ">")
    def >=[A](b: NonNumericalExpression[A]) = new BinaryOperatorNodeLogicalBoolean(this, b, ">=")
    def < [A](b: NonNumericalExpression[A]) = new BinaryOperatorNodeLogicalBoolean(this, b, "<")
    def <=[A](b: NonNumericalExpression[A]) = new BinaryOperatorNodeLogicalBoolean(this, b, "<=")

    def ||[B](e: TypedExpressionNode[B]) = new ConcatOp(this,e)

    def isNull = new PostfixOperatorNode("is null", this) with LogicalBoolean

    def isNotNull = new PostfixOperatorNode("is not null", this) with LogicalBoolean

    def in[A](e: Query[A]) = new BinaryOperatorNodeLogicalBoolean(this, e.ast, "in")
  }

  trait BooleanExpression[A] extends NonNumericalExpression[A] {
    def ~ = this
  }

  trait StringExpression[A] extends NonNumericalExpression[A] {

    def in(e: ListString) = new BinaryOperatorNodeLogicalBoolean(this, e, "in")

    //def between(lower: BaseScalarString, upper: BaseScalarString): LogicalBoolean = error("implement me") //new BinaryOperatorNode(this, lower, div) with LogicalBoolean
    def like(e: StringExpression[_])  = new BinaryOperatorNodeLogicalBoolean(this, e, "like")

    def ~ = this
  }

  trait DateExpression[A] extends NonNumericalExpression[A] {
    def ~ = this
  }


  
  protected implicit def sampleByte: ByteType
  protected implicit def sampleInt: IntType
  protected implicit def sampleString: StringType
  protected implicit def sampleDouble: DoubleType
  protected implicit def sampleFloat: FloatType
  protected implicit def sampleLong: LongType
  protected implicit def sampleBoolean: BooleanType
  protected implicit def sampleDate: DateType

  protected implicit val sampleByteO = Some(sampleByte)
  protected implicit val sampleIntO = Some(sampleInt)
  protected implicit val sampleStringO = Some(sampleString)
  protected implicit val sampleDoubleO = Some(sampleDouble)
  protected implicit val sampleFloatO = Some(sampleFloat)
  protected implicit val sampleLongO = Some(sampleLong)
  protected implicit val sampleBooleanO = Some(sampleBoolean)
  protected implicit val sampleDateO = Some(sampleDate)  
}