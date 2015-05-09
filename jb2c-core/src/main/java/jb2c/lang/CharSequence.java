package jb2c.lang;

/**
 * See also {@link java.lang.CharSequence}.
 *
 * @author Alexander Shabanov
 */
public interface CharSequence {
  int length();

  char charAt(int index);

  CharSequence subSequence(int start, int end);
}
