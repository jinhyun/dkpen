var editor, editorConfig;

var editorInit = function() {
  editor = CKEDITOR;
  editor.replace('editor1');

  editorConfig = editor.config;
  editorConfig.width = 700;
  editorConfig.height = 200;
};

var editorReadOnly = function() {
  editor.config.readOnly = true;
};
