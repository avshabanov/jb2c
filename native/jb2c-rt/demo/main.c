#include <stdio.h>

#include "jb2c-rt.h"

/* foo.Bar interface */
static const char j_foo_Bar_name[] = "foo.Bar";

struct j_foo_Bar_itbl {
  const char * $interface_name;
  int (* apply)(void * $p, int value);
};

/* foo.BarImpl class */
static const char j_foo_BarImpl_name[] = "foo.BarImpl";

struct j_foo_BarImpl {
  struct jb2c_class_meta * $meta;

  int a;
  int b;
};
 
static int j_foo_BarImpl_method_apply(void * $p, int value) {
  struct j_foo_BarImpl * $this = $p;
  return value * $this->a + $this->b;
}
 
static struct j_foo_Bar_itbl j_foo_BarImpl_itbl_j_foo_Bar_itbl = {
  &j_foo_Bar_name[0],
  &j_foo_BarImpl_method_apply
};
 
static void * j_foo_BarImpl_itbls[] = {
  &j_foo_BarImpl_itbl_j_foo_Bar_itbl
};
 
static struct jb2c_class_meta j_foo_BarImpl_meta = {
  .vtbl = NULL,

  .itbls = j_foo_BarImpl_itbls,
  .itbls_count = 1,

  .class_name = &j_foo_BarImpl_name[0],
  .object_size = sizeof(struct j_foo_BarImpl)
};

/*
 * invoke method
 */

static void invoke_apply(void * obj) {
  JB2C_INIT_KNOWN_ITBL(j_foo_Bar_itbl, foo_bar_itbl, obj, j_foo_Bar_name);
  int result = foo_bar_itbl->apply(obj, 3);
  fprintf(stdout, "foo.apply(3) = %d\n", result);
}

int main(int argc, char ** argv) {
  fprintf(stdout, "argc=%d\n", argc);

  {
    struct j_foo_BarImpl * obj = jb2c_malloc(sizeof(struct j_foo_BarImpl));
    obj->$meta = &j_foo_BarImpl_meta;

    obj->a = 100;
    obj->b = 7;

    invoke_apply(obj);

    jb2c_free(obj);
  }

  return 0;
}
