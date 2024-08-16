import { render, screen } from "@testing-library/react";
import Dropdown from "../../components/Dropdown/Dropdown";

describe("Dropdown", () => {
  it("should render a dropdown with 2 items", () => {
    const data = [{ model: "excluir" }, { model: "editar" }];

    const renderAiTagMenu = (item) => {
      return (
        <>
          <p className="assistentTitle">{item.model}</p>
        </>
      );
    };

    render(<Dropdown data={data} renderItem={renderAiTagMenu} />);
    const items = screen.getAllByText(/excluir|editar/i);
    expect(items.length).toBe(2);
    expect(items[0]).toHaveTextContent(/excluir/i);
    expect(items[1]).toHaveTextContent(/editar/i);
  });
});
