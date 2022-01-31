
const path = require("path");
const HtmlWebpackPlugin = require("html-webpack-plugin");

module.exports = {
  entry: "./src/index.js",
  plugins: [
    new HtmlWebpackPlugin({
      template: path.join(__dirname, "src/index.js"),
      filename: "index.html",
    }),
  ],
  resolve: {
    extensions: [".jsx", ".js"],
  },
  output: {
    filename: "[name].[contenthash].js",
    path: path.resolve(__dirname, "dist"),
  },
};
