import { render, screen } from "@testing-library/react";
import Button from "../../components/Button/Button";
import "@testing-library/jest-dom";

describe("Button", () => {
  it("should render button which contains the word provided", () => {
    render(<Button content="Test" />);

    const button = screen.getByRole("button");
    expect(button).toHaveTextContent(/test/i);
  });
});
