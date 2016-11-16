var afm = function (Kotlin) {
  'use strict';
  var _ = Kotlin.defineRootPackage(null, /** @lends _ */ {
    com: Kotlin.definePackage(null, /** @lends _.com */ {
      cout970: Kotlin.definePackage(null, /** @lends _.com.cout970 */ {
        afm: Kotlin.definePackage(null, /** @lends _.com.cout970.afm */ {
          main_kand9s$: function (args) {
            Kotlin.println('Starting');
            _.com.cout970.afm.controller.MainController.init();
          },
          api: Kotlin.definePackage(null, /** @lends _.com.cout970.afm.api */ {
            File: Kotlin.createClass(null, function File(path) {
              this.path = path;
              this.parentFile = null;
              this.isFile = false;
              this.name = this.path;
              this.isDirectory = false;
            }, /** @lends _.com.cout970.afm.api.File.prototype */ {
              listFiles: function () {
                return Kotlin.kotlin.collections.emptyList();
              },
              component1: function () {
                return this.path;
              },
              copy_61zpoe$: function (path) {
                return new _.com.cout970.afm.api.File(path === void 0 ? this.path : path);
              },
              toString: function () {
                return 'File(path=' + Kotlin.toString(this.path) + ')';
              },
              hashCode: function () {
                var result = 0;
                result = result * 31 + Kotlin.hashCode(this.path) | 0;
                return result;
              },
              equals_za3rmp$: function (other) {
                return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && Kotlin.equals(this.path, other.path))));
              }
            }),
            IColumn: Kotlin.createTrait(null),
            ChangeAction: Kotlin.createEnumClass(function () {
              return [Kotlin.Enum];
            }, function ChangeAction() {
              ChangeAction.baseInitializer.call(this);
            }, function () {
              return {
                ADD: function () {
                  return new _.com.cout970.afm.api.ChangeAction();
                },
                REMOVE: function () {
                  return new _.com.cout970.afm.api.ChangeAction();
                },
                UPDATE: function () {
                  return new _.com.cout970.afm.api.ChangeAction();
                }
              };
            }),
            IElement: Kotlin.createTrait(null),
            IEventProvider: Kotlin.createTrait(null),
            IView: Kotlin.createTrait(null),
            IColumnRender: Kotlin.createTrait(null)
          }),
          controller: Kotlin.definePackage(function () {
            this.KEY_RIGHT = 39;
            this.KEY_LEFT = 37;
            this.KEY_UP = 37;
            this.KEY_DOWN = 40;
          }, /** @lends _.com.cout970.afm.controller */ {
            EventProvider: Kotlin.createObject(null, function EventProvider() {
            }, /** @lends _.com.cout970.afm.controller.EventProvider.prototype */ {
              registerKeyListener_4z6kj7$: function (listener) {
                window.onkeydown = _.com.cout970.afm.controller.EventProvider.registerKeyListener_4z6kj7$f(listener);
              }
            }, /** @lends _.com.cout970.afm.controller.EventProvider */ {
              registerKeyListener_4z6kj7$f: function (closure$listener) {
                return function (e) {
                  var tmp$0, tmp$1;
                  if ((tmp$1 = Kotlin.isType(tmp$0 = e, KeyboardEvent) ? tmp$0 : null) != null) {
                    var $receiver = tmp$1;
                    var block$result;
                    closure$listener($receiver.keyCode);
                    return block$result;
                  }
                   else
                    return null;
                };
              }
            }),
            MainController: Kotlin.createObject(function () {
              return [_.com.cout970.afm.api.IView];
            }, function MainController() {
              this.columns_3sdr0z$_0 = Kotlin.kotlin.collections.listOf_9mqe4v$([new _.com.cout970.afm.model.Column(), new _.com.cout970.afm.model.Column(), new _.com.cout970.afm.model.Column()]);
              this.selectedColumnIndex = 0;
              this.columnRenderers_3sdr0z$_0 = Kotlin.kotlin.collections.listOf_9mqe4v$([new _.com.cout970.afm.view.ColumnRenderer(this.columns.get_za3lpa$(0)), new _.com.cout970.afm.view.ColumnRenderer(this.columns.get_za3lpa$(1)), new _.com.cout970.afm.view.ColumnRenderer(this.columns.get_za3lpa$(2))]);
            }, /** @lends _.com.cout970.afm.controller.MainController.prototype */ {
              columns: {
                get: function () {
                  return this.columns_3sdr0z$_0;
                }
              },
              selectedColumn: {
                get: function () {
                  return this.columns.get_za3lpa$(this.selectedColumnIndex);
                }
              },
              columnRenderers: {
                get: function () {
                  return this.columnRenderers_3sdr0z$_0;
                }
              },
              init: function () {
                _.com.cout970.afm.controller.EventProvider.registerKeyListener_4z6kj7$(_.com.cout970.afm.controller.MainController.init$f(this));
                this.columns.get_za3lpa$(0).add_ilgutd$(new _.com.cout970.afm.model.Element(new _.com.cout970.afm.api.File('Test element')));
              },
              update: function () {
                var tmp$1;
                tmp$1 = Kotlin.kotlin.collections.withIndex_q5oq31$(this.columnRenderers).iterator();
                while (tmp$1.hasNext()) {
                  var tmp$0 = tmp$1.next()
                  , j = tmp$0.component1()
                  , i = tmp$0.component2();
                  i.render_p42jm6$(this, j);
                }
              },
              createRootElement: function () {
                return new _.com.cout970.afm.model.Element(new _.com.cout970.afm.api.File('/'));
              },
              updateRight_0: function (index) {
                this.columns.get_za3lpa$(index + 1).clear();
                var tmp$0;
                tmp$0 = this.columns.get_za3lpa$(index).elements.get_za3lpa$(this.columns.get_za3lpa$(index).selectedIndex).getSubElements().iterator();
                while (tmp$0.hasNext()) {
                  var element = tmp$0.next();
                  this.columns.get_za3lpa$(index + 1).add_ilgutd$(element);
                }
                if (!this.columns.get_za3lpa$(index + 1).elements.isEmpty()) {
                  this.columns.get_za3lpa$(index + 1).selectedIndex = 0;
                }
              },
              moveRight: function () {
                var tmp$0, tmp$1, tmp$2, tmp$3;
                if (this.selectedColumnIndex === 0) {
                  if (this.selectedColumn.selectedElement == null) {
                    if (!this.selectedColumn.elements.isEmpty()) {
                      this.selectedColumn.selectedIndex = 0;
                    }
                  }
                  if (this.selectedColumn.selectedElement != null) {
                    var elem = (tmp$0 = this.selectedColumn.selectedElement) != null ? tmp$0 : Kotlin.throwNPE();
                    var sub = elem.getSubElements();
                    if (!sub.isEmpty()) {
                      this.columns.get_za3lpa$(1).clear();
                      var col = this.columns.get_za3lpa$(1);
                      var tmp$4;
                      tmp$4 = sub.iterator();
                      while (tmp$4.hasNext()) {
                        var element = tmp$4.next();
                        col.add_ilgutd$(element);
                      }
                      this.columns.get_za3lpa$(1).selectedIndex = 0;
                      this.selectedColumnIndex = 1;
                      var subsub = ((tmp$1 = this.columns.get_za3lpa$(1).selectedElement) != null ? tmp$1 : Kotlin.throwNPE()).getSubElements();
                      this.columns.get_za3lpa$(2).clear();
                      if (!subsub.isEmpty()) {
                        var col_0 = this.columns.get_za3lpa$(2);
                        var tmp$5;
                        tmp$5 = subsub.iterator();
                        while (tmp$5.hasNext()) {
                          var element_0 = tmp$5.next();
                          col_0.add_ilgutd$(element_0);
                        }
                        this.columns.get_za3lpa$(2).selectedIndex = 0;
                      }
                      this.update();
                    }
                  }
                }
                 else {
                  if (this.selectedColumn.selectedElement == null) {
                    if (!this.selectedColumn.elements.isEmpty()) {
                      this.selectedColumn.selectedIndex = 0;
                    }
                  }
                  if (this.selectedColumn.selectedElement != null) {
                    var elem_0 = (tmp$2 = this.selectedColumn.selectedElement) != null ? tmp$2 : Kotlin.throwNPE();
                    var sub_0 = elem_0.getSubElements();
                    if (!sub_0.isEmpty()) {
                      this.columns.get_za3lpa$(0).clear();
                      var col_1 = this.columns.get_za3lpa$(0);
                      var tmp$6;
                      tmp$6 = this.columns.get_za3lpa$(1).elements.iterator();
                      while (tmp$6.hasNext()) {
                        var element_1 = tmp$6.next();
                        col_1.add_ilgutd$(element_1.copy());
                      }
                      this.columns.get_za3lpa$(0).selectedIndex = this.columns.get_za3lpa$(1).selectedIndex;
                      this.columns.get_za3lpa$(1).clear();
                      var col_2 = this.columns.get_za3lpa$(1);
                      var tmp$7;
                      tmp$7 = sub_0.iterator();
                      while (tmp$7.hasNext()) {
                        var element_2 = tmp$7.next();
                        col_2.add_ilgutd$(element_2);
                      }
                      this.columns.get_za3lpa$(1).selectedIndex = 0;
                      var subsub_0 = ((tmp$3 = this.columns.get_za3lpa$(1).selectedElement) != null ? tmp$3 : Kotlin.throwNPE()).getSubElements();
                      this.columns.get_za3lpa$(2).clear();
                      if (!subsub_0.isEmpty()) {
                        var col_3 = this.columns.get_za3lpa$(2);
                        var tmp$8;
                        tmp$8 = subsub_0.iterator();
                        while (tmp$8.hasNext()) {
                          var element_3 = tmp$8.next();
                          col_3.add_ilgutd$(element_3);
                        }
                        this.columns.get_za3lpa$(2).selectedIndex = 0;
                      }
                      this.update();
                    }
                  }
                }
              },
              moveLeft: function () {
                var tmp$0, tmp$1, tmp$2, tmp$3, tmp$4;
                if (this.selectedColumnIndex === 1) {
                  if (this.selectedColumn.selectedElement == null) {
                    if (!this.selectedColumn.elements.isEmpty()) {
                      this.selectedColumn.selectedIndex = 0;
                    }
                  }
                  if (this.selectedColumn.selectedElement != null) {
                    var elem = (tmp$0 = this.selectedColumn.selectedElement) != null ? tmp$0 : Kotlin.throwNPE();
                    if (((tmp$2 = (tmp$1 = elem.parent) != null ? tmp$1.parent : null) != null ? tmp$2.parent : null) == null) {
                      this.selectedColumnIndex = 0;
                      var tmp = this.columns.get_za3lpa$(0).selectedIndex;
                      this.columns.get_za3lpa$(0).clear();
                      var col = this.columns.get_za3lpa$(0);
                      var tmp$5;
                      tmp$5 = this.createRootElement().getSubElements().iterator();
                      while (tmp$5.hasNext()) {
                        var element = tmp$5.next();
                        col.add_ilgutd$(element);
                      }
                      this.columns.get_za3lpa$(0).selectedIndex = tmp;
                      this.columns.get_za3lpa$(2).clear();
                      this.update();
                    }
                     else {
                      this.columns.get_za3lpa$(2).clear();
                      var col_0 = this.columns.get_za3lpa$(2);
                      var tmp$6;
                      tmp$6 = this.columns.get_za3lpa$(1).elements.iterator();
                      while (tmp$6.hasNext()) {
                        var element_0 = tmp$6.next();
                        col_0.add_ilgutd$(element_0.copy());
                      }
                      this.columns.get_za3lpa$(2).selectedIndex = this.columns.get_za3lpa$(1).selectedIndex;
                      this.columns.get_za3lpa$(1).clear();
                      var col_1 = this.columns.get_za3lpa$(1);
                      var tmp$7;
                      tmp$7 = this.columns.get_za3lpa$(0).elements.iterator();
                      while (tmp$7.hasNext()) {
                        var element_1 = tmp$7.next();
                        col_1.add_ilgutd$(element_1.copy());
                      }
                      this.columns.get_za3lpa$(1).selectedIndex = this.columns.get_za3lpa$(0).selectedIndex;
                      var oldSelected = (tmp$4 = ((tmp$3 = elem.parent) != null ? tmp$3 : Kotlin.throwNPE()).parent) != null ? tmp$4 : Kotlin.throwNPE();
                      this.columns.get_za3lpa$(0).clear();
                      var col_2 = this.columns.get_za3lpa$(0);
                      var tmp$9;
                      var tmp$8;
                      tmp$8 = ((tmp$9 = oldSelected.parent) != null ? tmp$9 : Kotlin.throwNPE()).getSubElements().iterator();
                      while (tmp$8.hasNext()) {
                        var element_2 = tmp$8.next();
                        col_2.add_ilgutd$(element_2);
                      }
                      this.update();
                    }
                  }
                }
                 else {
                }
              }
            }, /** @lends _.com.cout970.afm.controller.MainController */ {
              init$f: function (this$MainController) {
                return function (e) {
                  var tmp$0, tmp$1, tmp$2, tmp$3, tmp$4, tmp$5;
                  if (e === _.com.cout970.afm.controller.KEY_RIGHT) {
                    this$MainController.moveRight();
                  }
                   else if (e === _.com.cout970.afm.controller.KEY_LEFT) {
                    this$MainController.moveLeft();
                  }
                   else if (e === _.com.cout970.afm.controller.KEY_DOWN) {
                    if (this$MainController.selectedColumn.selectedIndex < this$MainController.selectedColumn.elements.size - 1) {
                      tmp$0 = this$MainController.selectedColumn;
                      tmp$1 = tmp$0.selectedIndex, tmp$2 = tmp$1, tmp$0.selectedIndex = tmp$1 + 1, tmp$2;
                      this$MainController.updateRight_0(this$MainController.selectedColumnIndex);
                      this$MainController.update();
                    }
                  }
                   else if (e === _.com.cout970.afm.controller.KEY_UP) {
                    if (this$MainController.selectedColumn.selectedIndex > 0) {
                      tmp$3 = this$MainController.selectedColumn;
                      tmp$4 = tmp$3.selectedIndex, tmp$5 = tmp$4, tmp$3.selectedIndex = tmp$4 - 1, tmp$5;
                      this$MainController.updateRight_0(this$MainController.selectedColumnIndex);
                      this$MainController.update();
                    }
                  }
                  this$MainController.update();
                };
              }
            })
          }),
          model: Kotlin.definePackage(null, /** @lends _.com.cout970.afm.model */ {
            Column: Kotlin.createClass(function () {
              return [_.com.cout970.afm.api.IColumn];
            }, function Column() {
              this.selectedIndex_qkd7lx$_0 = -1;
              this.elementList = Kotlin.kotlin.collections.mutableListOf_9mqe4v$([]);
              this.listeners = Kotlin.kotlin.collections.mutableListOf_9mqe4v$([]);
            }, /** @lends _.com.cout970.afm.model.Column.prototype */ {
              selectedIndex: {
                get: function () {
                  return this.selectedIndex_qkd7lx$_0;
                },
                set: function (selectedIndex) {
                  this.selectedIndex_qkd7lx$_0 = selectedIndex;
                }
              },
              elements: {
                get: function () {
                  return this.elementList;
                }
              },
              selectedElement: {
                get: function () {
                  return Kotlin.kotlin.collections.get_indices_mwto7b$(this.elements).contains_htax2k$(this.selectedIndex) ? this.elements.get_za3lpa$(this.selectedIndex) : null;
                }
              },
              addListener_fpz1yq$: function (listener) {
                Kotlin.kotlin.collections.plusAssign_4kvzvw$(this.listeners, listener);
              },
              add_ilgutd$: function (e) {
                this.elementList.add_za3rmp$(e);
                e.column = this;
                var tmp$0;
                tmp$0 = this.listeners.iterator();
                while (tmp$0.hasNext()) {
                  var element = tmp$0.next();
                  element(Kotlin.kotlin.collections.get_lastIndex_a7ptmv$(this.elementList), e, _.com.cout970.afm.api.ChangeAction.ADD);
                }
              },
              remove_za3lpa$: function (index) {
                if (Kotlin.kotlin.collections.get_indices_mwto7b$(this.elementList).contains_htax2k$(index)) {
                  var tmp$0;
                  tmp$0 = this.listeners.iterator();
                  while (tmp$0.hasNext()) {
                    var element = tmp$0.next();
                    element(index, this.elementList.get_za3lpa$(index), _.com.cout970.afm.api.ChangeAction.REMOVE);
                  }
                  this.elementList.removeAt_za3lpa$(index);
                }
              },
              remove_ilgutd$: function (e) {
                if (this.elementList.contains_za3rmp$(e)) {
                  var tmp$0;
                  tmp$0 = this.listeners.iterator();
                  while (tmp$0.hasNext()) {
                    var element = tmp$0.next();
                    element(this.elementList.indexOf_za3rmp$(e), e, _.com.cout970.afm.api.ChangeAction.REMOVE);
                  }
                  this.elementList.remove_za3rmp$(e);
                }
              },
              clear: function () {
                var tmp$0;
                var index = 0;
                tmp$0 = this.elementList.iterator();
                while (tmp$0.hasNext()) {
                  var item = tmp$0.next();
                  var i = index++;
                  var tmp$1;
                  tmp$1 = this.listeners.iterator();
                  while (tmp$1.hasNext()) {
                    var element = tmp$1.next();
                    element(i, item, _.com.cout970.afm.api.ChangeAction.REMOVE);
                  }
                }
                this.elementList.clear();
              }
            }, /** @lends _.com.cout970.afm.model.Column */ {
            }),
            Element: Kotlin.createClass(function () {
              return [_.com.cout970.afm.api.IElement];
            }, function Element$(file) {
              this.file_inrscj$_0 = file;
              this.column_inrscj$_0 = null;
            }, /** @lends _.com.cout970.afm.model.Element.prototype */ {
              file: {
                get: function () {
                  return this.file_inrscj$_0;
                }
              },
              displayName: {
                get: function () {
                  return this.file.name;
                }
              },
              column: {
                get: function () {
                  return this.column_inrscj$_0;
                },
                set: function (column) {
                  this.column_inrscj$_0 = column;
                }
              },
              isSelected: function () {
                var tmp$0;
                return Kotlin.equals((tmp$0 = this.column) != null ? tmp$0.selectedElement : null, this);
              },
              getSubElements: function () {
                var subFiles = this.file.listFiles();
                var destination = Kotlin.kotlin.collections.ArrayList_init_za3lpa$(Kotlin.kotlin.collections.collectionSizeOrDefault_0(subFiles, 10));
                var tmp$0;
                tmp$0 = subFiles.iterator();
                while (tmp$0.hasNext()) {
                  var item = tmp$0.next();
                  destination.add_za3rmp$(_.com.cout970.afm.model.Element.Companion.create_aw98h6$(item));
                }
                return destination;
              },
              parent: {
                get: function () {
                  var tmp$0, tmp$1;
                  if (this.file.parentFile == null) {
                    return null;
                  }
                   else if (((tmp$0 = this.file.parentFile.parentFile) != null ? tmp$0.listFiles() : null) == null || this.file.parentFile.parentFile.listFiles().size !== 1) {
                    return new _.com.cout970.afm.model.Element(this.file.parentFile);
                  }
                   else {
                    var list = Kotlin.kotlin.collections.mutableListOf_9mqe4v$([]);
                    var lastFile = this.file.parentFile;
                    var sub;
                    while (true) {
                      list.add_za3rmp$(new _.com.cout970.afm.model.Element(lastFile != null ? lastFile : Kotlin.throwNPE()));
                      sub = ((tmp$1 = lastFile.parentFile) != null ? tmp$1 : Kotlin.throwNPE()).listFiles();
                      Kotlin.println(sub);
                      if (sub.size !== 1) {
                        break;
                      }
                       else {
                        lastFile = lastFile.parentFile;
                      }
                    }
                    return new _.com.cout970.afm.model.Element.ComponentConsoleElement(list);
                  }
                }
              },
              copy: function () {
                return new _.com.cout970.afm.model.Element(this.file);
              },
              component1: function () {
                return this.file;
              },
              copy_aw98h6$: function (file) {
                return new _.com.cout970.afm.model.Element(file === void 0 ? this.file : file);
              },
              toString: function () {
                return 'Element(file=' + Kotlin.toString(this.file) + ')';
              },
              hashCode: function () {
                var result = 0;
                result = result * 31 + Kotlin.hashCode(this.file) | 0;
                return result;
              },
              equals_za3rmp$: function (other) {
                return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && Kotlin.equals(this.file, other.file))));
              }
            }, /** @lends _.com.cout970.afm.model.Element */ {
              Companion: Kotlin.createObject(null, function Companion() {
              }, /** @lends _.com.cout970.afm.model.Element.Companion.prototype */ {
                create_aw98h6$: function (file) {
                  return new _.com.cout970.afm.model.Element(file);
                }
              }),
              object_initializer$: function () {
                _.com.cout970.afm.model.Element.Companion;
              },
              ComponentConsoleElement: Kotlin.createClass(function () {
                return [_.com.cout970.afm.api.IElement];
              }, function ComponentConsoleElement(elements) {
                this.elements = elements;
                this.column_dbparb$_0 = null;
              }, /** @lends _.com.cout970.afm.model.Element.ComponentConsoleElement.prototype */ {
                displayName: {
                  get: function () {
                    return Kotlin.kotlin.collections.joinToString_ld60a2$(this.elements, '.', void 0, void 0, void 0, void 0, _.com.cout970.afm.model.Element.ComponentConsoleElement.get_displayName$f);
                  }
                },
                file: {
                  get: function () {
                    return Kotlin.kotlin.collections.last_a7ptmv$(this.elements).file;
                  }
                },
                column: {
                  get: function () {
                    return this.column_dbparb$_0;
                  },
                  set: function (column) {
                    this.column_dbparb$_0 = column;
                  }
                },
                parent: {
                  get: function () {
                    return Kotlin.kotlin.collections.first_a7ptmv$(this.elements).parent;
                  }
                },
                copy: function () {
                  return new _.com.cout970.afm.model.Element.ComponentConsoleElement(this.elements);
                },
                isSelected: function () {
                  var tmp$0;
                  return Kotlin.equals((tmp$0 = this.column) != null ? tmp$0.selectedElement : null, this);
                },
                getSubElements: function () {
                  var subFiles = this.file.listFiles();
                  var destination = Kotlin.kotlin.collections.ArrayList_init_za3lpa$(Kotlin.kotlin.collections.collectionSizeOrDefault_0(subFiles, 10));
                  var tmp$0;
                  tmp$0 = subFiles.iterator();
                  while (tmp$0.hasNext()) {
                    var item = tmp$0.next();
                    destination.add_za3rmp$(_.com.cout970.afm.model.Element.Companion.create_aw98h6$(item));
                  }
                  return destination;
                },
                component1: function () {
                  return this.elements;
                },
                copy_dctw85$: function (elements) {
                  return new _.com.cout970.afm.model.Element.ComponentConsoleElement(elements === void 0 ? this.elements : elements);
                },
                toString: function () {
                  return 'ComponentConsoleElement(elements=' + Kotlin.toString(this.elements) + ')';
                },
                hashCode: function () {
                  var result = 0;
                  result = result * 31 + Kotlin.hashCode(this.elements) | 0;
                  return result;
                },
                equals_za3rmp$: function (other) {
                  return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && Kotlin.equals(this.elements, other.elements))));
                }
              }, /** @lends _.com.cout970.afm.model.Element.ComponentConsoleElement */ {
                get_displayName$f: function (it) {
                  return it.displayName;
                }
              })
            })
          }),
          view: Kotlin.definePackage(null, /** @lends _.com.cout970.afm.view */ {
            ColumnRenderer: Kotlin.createClass(function () {
              return [_.com.cout970.afm.api.IColumnRender];
            }, function ColumnRenderer(column) {
              this.column_8ki3n6$_0 = column;
            }, /** @lends _.com.cout970.afm.view.ColumnRenderer.prototype */ {
              column: {
                get: function () {
                  return this.column_8ki3n6$_0;
                },
                set: function (column) {
                  this.column_8ki3n6$_0 = column;
                }
              },
              height: {
                get: function () {
                  return this.column.elements.size * 22;
                }
              },
              width: {
                get: function () {
                  return 200;
                }
              },
              render_p42jm6$: function (view_0, index) {
                var tmp$1, tmp$3, tmp$4, tmp$5;
                if (!this.column.elements.isEmpty()) {
                  if (index === 0)
                    tmp$1 = 5;
                  else if (index === 1)
                    tmp$1 = view_0.columnRenderers.get_za3lpa$(0).width + 15;
                  else if (index === 2)
                    tmp$1 = view_0.columnRenderers.get_za3lpa$(0).width + view_0.columnRenderers.get_za3lpa$(1).width + 25;
                  else
                    tmp$1 = 0;
                  var offset = tmp$1;
                  var builder = new Kotlin.StringBuilder();
                  builder.append('<div style="outline: solid white 1px;">');
                  tmp$3 = Kotlin.kotlin.collections.withIndex_q5oq31$(this.column.elements).iterator();
                  while (tmp$3.hasNext()) {
                    var tmp$2 = tmp$3.next()
                    , i = tmp$2.component1()
                    , e = tmp$2.component2();
                    this.printElement_0(i, e, view_0, builder);
                  }
                  builder.append('<\/div>');
                  var code = builder.toString();
                  Kotlin.println(code);
                  var elem = document.getElementById('column' + index);
                  var tmp$6;
                  if ((tmp$5 = Kotlin.isType(tmp$4 = elem, Element) ? tmp$4 : null) != null) {
                    var block$result;
                    tmp$5.innerHTML = code;
                    tmp$6 = block$result;
                  }
                   else
                    tmp$6 = null;
                  tmp$6;
                }
              },
              printElement_0: function (i, e, view_0, builder) {
                var background = null;
                if (e.isSelected()) {
                  if (Kotlin.equals(this.column, view_0.selectedColumn)) {
                    background = 'blue';
                  }
                   else if (!Kotlin.equals(view_0.columns.get_za3lpa$(2), this.column)) {
                    background = '#4040FF';
                  }
                }
                if (background != null) {
                  builder.append('<div style=' + '"' + 'background-color: ' + Kotlin.toString(background) + ';' + '"' + '>');
                }
                 else {
                  builder.append('<div>');
                }
                if (e.file.isDirectory) {
                  builder.append('<span style="color: white">');
                }
                 else {
                  builder.append('<span style="color: green">');
                }
                builder.append(e.displayName);
                builder.append('<\/span>');
                builder.append('<\/div>');
              }
            }, /** @lends _.com.cout970.afm.view.ColumnRenderer */ {
            })
          })
        })
      })
    })
  });
  Kotlin.defineModule('afm', _);
  _.com.cout970.afm.main_kand9s$([]);
  return _;
}(kotlin);
