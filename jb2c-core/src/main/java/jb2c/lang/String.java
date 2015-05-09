package jb2c.lang;

import java.lang.*;

/**
 * See also {@link java.lang.String}.
 *
 * @author Alexander Shabanov
 */
public final class String implements CharSequence {
  private final char[] buf;

  public String(char[] buf) {
    this.buf = buf; // TODO: Arrays.copyOf(buf, buf.length)
  }

  @Override
  public int length() {
    return buf.length;
  }

  @Override
  public char charAt(int index) {
    return buf[index];
  }

  @Override
  public CharSequence subSequence(int start, int end) {
    throw new UnsupportedOperationException(); // TODO: impl
  }
}
