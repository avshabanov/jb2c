/*
 * Runtime Definitions for JB2C
 */

#pragma once

#include <stddef.h>

#ifdef __cplusplus
extern "C" {
#endif

struct jb2c_class_meta {
  /* virtual methods table */
  void * vtbl;

  /* interfaces */
  void ** itbls;
  int itbls_count;

  /* parent */
  struct jb2c_class_meta * parent;

  /* object meta information */
  const char * class_name;
  size_t object_size;
};

struct jb2c_itbl_base {
  const char * interface_name;
};

struct jb2c_vtbl_base {
  struct jb2c_class_meta * parent_class_meta;
};

struct jb2c_object_base {
  struct jb2c_class_meta * meta;
};

/*
 * Memory
 */

extern void * jb2c_malloc(size_t size);
extern void jb2c_free(void * p);

/*
 * Logging and fatals reporting
 */

extern void jb2c_rt_fatal(const char * fmt, ...);

/*
 * Casting support
 */

extern struct jb2c_itbl_base *
jb2c_rt_find_itbl_unopt(const char * interface_name, void ** itbls, int itbls_count);

/**
 * Optimistic version for searching interface table.
 *
 * @param o Object header
 * @param interface_name Interface name
 * @return Interface table
 */
static inline struct jb2c_itbl_base *
jb2c_rt_find_itbl(struct jb2c_object_base * o, const char * interface_name) {
  void ** itbls = o->meta->itbls;
  int itbls_count = o->meta->itbls_count;
  struct jb2c_itbl_base * itbl = itbls[0]; // we should have at least one interface here due to compile check

  // optimized version: if first element is a vtbl - return it at once
  if (itbl->interface_name == interface_name) {
    return itbl;
  }

  return jb2c_rt_find_itbl_unopt(interface_name, itbls, itbls_count);
}


#ifdef __cplusplus
}
#endif

