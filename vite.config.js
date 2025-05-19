import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import checker from "vite-plugin-checker";
import eslint from "vite-plugin-eslint";
import wasm from "vite-plugin-wasm";
import topLevelAwait from "vite-plugin-top-level-await";
import wgslRollup from "@use-gpu/wgsl-loader/rollup";

export default defineConfig({
  test: {
    include: ["public/**/**test.mjs", "public/**/**test.jsx"],
  },
  plugins: [wasm(), topLevelAwait(), react(), wgslRollup()],
});
