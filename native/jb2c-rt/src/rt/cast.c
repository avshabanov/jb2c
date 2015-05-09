#include "jb2c-rt.h"

struct jb2c_itbl_base *
jb2c_rt_find_known_itbl_unopt(const char * interface_name, void ** itbls, int itbls_count) {
  for (int i = 1; i < itbls_count; ++i) {
    struct jb2c_itbl_base * itbl = itbls[i];
    if (itbl->interface_name == interface_name) {
      return itbl;
    }
  }

  jb2c_rt_fatal("Unable to find interface %s", interface_name); // shouldn't happen
  return NULL;
}

struct jb2c_itbl_base *
jb2c_rt_find_unknown_itbl(struct jb2c_object_base * o, const char * interface_name) {
  void ** itbls = o->meta->itbls;
  int itbls_count = o->meta->itbls_count;

  for (int i = 0; i < itbls_count; ++i) {
    struct jb2c_itbl_base * itbl = itbls[i];
    if (itbl->interface_name == interface_name) {
      return itbl;
    }
  }

  return NULL; // perfectly normal
}


