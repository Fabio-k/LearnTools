import { useRef, useEffect } from "react";
import {
  useEditor,
  EditorContent,
  FloatingMenu,
  BubbleMenu,
} from "@tiptap/react";
import StarterKit from "@tiptap/starter-kit";
import "./Editor.css";
import FloatingMenuButton from "../FloatingMenuButton/FloatingMenuButton.jsx";
import resumesService from "../../app/services/resumeService.js";

const extensions = [StarterKit];

const Editor = ({ resumeDisplay, dispatch }) => {
  let content;
  try {
    content = JSON.parse(resumeDisplay.description);
  } catch (error) {
    console.error("Failed to parse JSON:", error);
    content = resumeDisplay.description;
  }
  const editor = useEditor({
    extensions,
    content,
    editorProps: {
      attributes: {
        class: "editorDefault",
      },
    },
    onUpdate(editor) {
      debounceAutosave(editor);
    },
  });

  const debounceTimeout = useRef(null);

  const debounceAutosave = (editor) => {
    if (debounceTimeout.current) {
      clearTimeout(debounceTimeout.current);
    }
    debounceTimeout.current = setTimeout(async () => {
      const resumeService = new resumesService();
      if (resumeDisplay.id != 0) {
        const result = await resumeService.editResume(
          resumeDisplay.id,
          resumeDisplay.title,
          JSON.stringify(editor.editor.getJSON())
        );
        if (result) {
          dispatch({ type: "UPDATE", payload: result });
        }
      }
    }, 2000);
  };

  const clearCurrentNodeText = () => {
    const { state, dispatch } = editor.view;
    const { $from } = state.selection;
    const node = $from.node($from.depth);

    if (node && node.isTextblock) {
      const start = $from.start($from.depth);
      const end = $from.end($from.depth);
      const tr = state.tr.delete(start, end);
      dispatch(tr);
    }
  };
  const executeEditorCommand = (command, options = {}) => {
    clearCurrentNodeText();
    editor.chain().focus()[command](options).run();
  };

  useEffect(() => {
    let content;
    try {
      content = JSON.parse(resumeDisplay.description);
    } catch (error) {
      content = resumeDisplay.description;
    }

    if (resumeDisplay != null) {
      editor.commands.setContent(content);
    }
  }, [resumeDisplay.description, editor]);

  return (
    <>
      <EditorContent editor={editor} className="editorContent" />
      {editor && (
        <FloatingMenu
          editor={editor}
          shouldShow={({ state }) => {
            const { $from } = state.selection;
            const currentLineText = $from.nodeBefore?.textContent;
            return currentLineText == "/";
          }}
          className="floatingMenu"
        >
          <FloatingMenuButton
            img="https://www.notion.so/images/blocks/header.57a7576a.png"
            title="Header 1"
            description="grande subtítulo"
            editor={editor}
            onToggleHeading={() =>
              executeEditorCommand("toggleHeading", { level: 1 })
            }
          />
          <FloatingMenuButton
            img="https://www.notion.so/images/blocks/subheader.9aab4769.png"
            title="Header 2"
            description="Médio subtítulo"
            editor={editor}
            onToggleHeading={() =>
              executeEditorCommand("toggleHeading", { level: 2 })
            }
          />
          <FloatingMenuButton
            img="https://www.notion.so/images/blocks/subsubheader.d0ed0bb3.png"
            title="Header 3"
            description="pequeno subtítulo"
            editor={editor}
            onToggleHeading={() =>
              executeEditorCommand("toggleHeading", { level: 3 })
            }
          />
          <FloatingMenuButton
            img="https://www.notion.so/images/blocks/bulleted-list.0e87e917.png"
            title="lista simples"
            description="bulletlist"
            editor={editor}
            onToggleHeading={() => executeEditorCommand("toggleBulletList")}
          />

          <FloatingMenuButton
            img="https://www.notion.so/images/blocks/divider.210d0faf.png"
            title="divisor"
            description="separe conteudos"
            editor={editor}
            onToggleHeading={() => executeEditorCommand("setHorizontalRule")}
          />
        </FloatingMenu>
      )}
    </>
  );
};

export default Editor;
