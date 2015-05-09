#include "jb2c-rt.h"

struct jb2c_itbl_base *
jb2c_rt_find_itbl_unopt(const char * interface_name, void ** itbls, int itbls_count) {
  int i;

  for (i = 1; i < itbls_count; ++i) {
    struct jb2c_itbl_base * itbl = itbls[i];
    if (itbl->interface_name == interface_name) {
      return itbl;
    }
  }

  jb2c_rt_fatal("Unable to find interface %s", interface_name); // shouldn't happen
  return NULL;
}

