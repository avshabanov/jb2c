#include "jb2c-rt.h"

#include <stdlib.h>

void * jb2c_malloc(size_t size) {
  void * p = malloc(size);
  if (p == NULL) {
    jb2c_rt_fatal("Unable to alloc mem block of size %zu", size);
  }

  return p;
}

void jb2c_free(void * p) {
  free(p);
}
