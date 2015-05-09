#include "jb2c-rt.h"

#include <stdio.h>
#include <stdlib.h>
#include <stdarg.h>

void jb2c_rt_fatal(const char * fmt, ...) {
  va_list vl;
  FILE * out = stderr;

  fputs("\n=== FATAL ===\n", out);

  va_start(vl, fmt);
  vfprintf(out, fmt, vl);
  va_end(vl);

  fputs("\n=============\n", out);

  abort();
}
